using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RollCall.Migrations
{
    public partial class first12pp : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Leave",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    From = table.Column<DateTime>(nullable: false),
                    To = table.Column<DateTime>(nullable: false),
                    Type = table.Column<int>(nullable: false),
                    Motivation = table.Column<string>(nullable: true),
                    LeaveStatus = table.Column<int>(nullable: false),
                    UserId = table.Column<int>(nullable: true),
                    ApproverId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Leave", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Leave_User_ApproverId",
                        column: x => x.ApproverId,
                        principalTable: "User",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Leave_User_UserId",
                        column: x => x.UserId,
                        principalTable: "User",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "LeaveBalance",
                columns: table => new
                {
                    Id = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Annula = table.Column<decimal>(nullable: false),
                    Sick = table.Column<decimal>(nullable: false),
                    Study = table.Column<decimal>(nullable: false),
                    Maternity = table.Column<decimal>(nullable: false),
                    UserId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_LeaveBalance", x => x.Id);
                    table.ForeignKey(
                        name: "FK_LeaveBalance_User_UserId",
                        column: x => x.UserId,
                        principalTable: "User",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Leave_ApproverId",
                table: "Leave",
                column: "ApproverId");

            migrationBuilder.CreateIndex(
                name: "IX_Leave_UserId",
                table: "Leave",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_LeaveBalance_UserId",
                table: "LeaveBalance",
                column: "UserId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Leave");

            migrationBuilder.DropTable(
                name: "LeaveBalance");
        }
    }
}
