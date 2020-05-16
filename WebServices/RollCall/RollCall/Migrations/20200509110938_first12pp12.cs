using Microsoft.EntityFrameworkCore.Migrations;

namespace RollCall.Migrations
{
    public partial class first12pp12 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Leave_User_ApproverId",
                table: "Leave");

            migrationBuilder.DropIndex(
                name: "IX_Leave_ApproverId",
                table: "Leave");

            migrationBuilder.DropColumn(
                name: "ApproverId",
                table: "Leave");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "ApproverId",
                table: "Leave",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Leave_ApproverId",
                table: "Leave",
                column: "ApproverId");

            migrationBuilder.AddForeignKey(
                name: "FK_Leave_User_ApproverId",
                table: "Leave",
                column: "ApproverId",
                principalTable: "User",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
